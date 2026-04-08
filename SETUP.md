# Pipeline DevSecOps — Guía de Configuración

Guía completa para activar los dos pipelines. Todo el stack es **gratuito**.

---

## Estructura de archivos

```
tu-repositorio/
├── Makefile                        ← Pipeline 1: validaciones locales
├── .pre-commit-config.yaml         ← Pipeline 1: hooks de pre-commit
├── .secrets.baseline               ← Generado por detect-secrets (ver paso 2)
├── SETUP.md                        ← Esta guía de configuración
└── .github/
    └── workflows/
        ├── pr-security-gates.yml   ← Pipeline 2: gates del PR
        └── ci-cd-deploy.yml        ← Pipeline 2: CI/CD + deploy a Render
```

---

## Paso 1 — Instalar herramientas locales

### Requisitos del sistema

- **Java 17** y **Maven 3.9+** — necesarios para compilar el proyecto.
- **make** — ejecuta el Makefile de validaciones locales.

**En Windows (Git Bash / MINGW64):**

```bash
# make — descargar binario para Windows
curl -L -o /tmp/make.zip https://sourceforge.net/projects/ezwinports/files/make-4.4.1-without-guile-w32-bin.zip/download
unzip /tmp/make.zip -d /tmp/make
mkdir -p ~/bin && cp /tmp/make/bin/make.exe ~/bin/make.exe
echo 'export PATH="$HOME/bin:$PATH"' >> ~/.bashrc
export PATH="$HOME/bin:$PATH"
```

**En Linux / macOS:**

```bash
# make ya suele estar instalado. Si no:
sudo apt install make        # Debian/Ubuntu
brew install make             # macOS
```

### Herramientas Python

```bash
# detect-secrets, pre-commit y semgrep
pip install pre-commit detect-secrets semgrep

# Activar hooks en el repositorio local
pre-commit install

# Verificar instalación:
make --version
semgrep --version
detect-secrets --version
pre-commit --version
```

---

## Paso 2 — Inicializar baseline de secretos

```bash
# Genera el archivo de baseline (debe commitearse al repo)
detect-secrets scan > .secrets.baseline

# Si ya tienes falsos positivos conocidos, márcalos:
detect-secrets audit .secrets.baseline

# Verifica que el baseline está actualizado
detect-secrets scan --baseline .secrets.baseline
```

---

## Paso 3 — Configurar Secrets y Variables en GitHub

Ve a: **Settings → Secrets and variables → Actions**

### Repository Secrets (valores sensibles)

| Secret | Descripción | Requerido para |
|--------|-------------|----------------|
| `SONAR_TOKEN` | Token de autenticación de SonarCloud | Gate 1 |
| `RENDER_DEPLOY_HOOK_URL` | Deploy Hook URL de Render (ver paso 6) | CI/CD Deploy |
| `GITLEAKS_LICENSE` | Licencia de Gitleaks (solo repos privados) | Gate 4 |

### Repository Variables (valores no sensibles)

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `PRODUCTION_URL` | URL del servicio en Render | `https://tu-app.onrender.com` |

---

## Paso 4 — Proteger la rama principal

Ve a: **Settings → Branches → Add branch protection rule**

Configura para `main`:

```
✅ Require a pull request before merging
✅ Require approvals: 1
✅ Require status checks to pass before merging
   → Agrega: "✅ PR Security Gates — Resumen"
✅ Require branches to be up to date before merging
✅ Do not allow bypassing the above settings
```

---

## Paso 5 — Activar GitHub Advanced Security

Ve a: **Settings → Security & analysis**

```
✅ Dependency graph              → ON
✅ Dependabot alerts             → ON
✅ Dependabot security updates   → ON
✅ Code scanning (CodeQL)        → ON  (usado como Gate 2 SAST)
✅ Secret scanning               → ON
✅ Push protection               → ON  ← Bloquea el push si detecta secretos
```

---

## Paso 6 — Configurar Render (deploy gratuito)

1. Crea cuenta en [render.com](https://render.com) con tu GitHub.
2. **New → Web Service** → Conecta el repositorio.
3. Configura:
   - **Name:** `suntea-back` (o el nombre que prefieras)
   - **Region:** Oregon (o la más cercana)
   - **Branch:** `main`
   - **Runtime:** `Docker` (si agregas Dockerfile) o `Native` con:
     - **Build Command:** `mvn -B clean package -DskipTests`
     - **Start Command:** `java -jar target/LabReserves-0.0.1-SNAPSHOT.jar`
   - **Instance Type:** `Free`
4. En **Environment**, agrega las variables de tu `application.properties`:
   - `SPRING_DATA_MONGODB_URI` → tu connection string de MongoDB Atlas
   - `SERVER_PORT` → `8080` (o el que Render asigne via `$PORT`)
5. Copia el **Deploy Hook URL** desde **Settings → Deploy Hook**.
6. Agrega ese URL como secret `RENDER_DEPLOY_HOOK_URL` en GitHub (paso 3).

> **Nota:** Render Free tier hiberna después de 15 min sin tráfico. El primer request tras hibernación tarda ~30s (cold start de Spring Boot).

---

## Paso 7 — Configurar SonarCloud

1. Ve a [sonarcloud.io](https://sonarcloud.io) y crea una cuenta con tu GitHub.
2. Importa el repositorio.
3. Obtén el `SONAR_TOKEN` desde **My Account → Security → Generate Token**.
4. El `pom.xml` ya tiene configuradas las propiedades de SonarCloud:

```xml
<properties>
  <sonar.organization>sergiobejarano</sonar.organization>
  <sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
```

---

## Flujo completo de trabajo

```
Desarrollador escribe código
         ↓
[SonarLint en IDE] → detecta vulnerabilidades en tiempo real
         ↓
$ make check       → lint + build + test + sast (Semgrep) + secrets
         ↓
$ git commit       → pre-commit hook ejecuta Semgrep SAST + detect-secrets
         ↓
$ git push
         ↓
[Pull Request en GitHub]
  ├── Gate 1: SonarCloud Quality Gate
  ├── Gate 2: CodeQL SAST
  ├── Gate 3: Trivy SCA
  └── Gate 4: Secret Scanning (Gitleaks + GHAS)
         ↓
[Merge habilitado solo si los 4 gates pasan]
         ↓
[Push a main → CI/CD se activa]
  ├── Build + pruebas (mvn verify)
  ├── Trivy escanea dependencias
  ├── Deploy a Render (via Deploy Hook)
  └── Health check post-despliegue
```

---

## Herramientas y costo

| Herramienta | Uso | Costo |
|-------------|-----|-------|
| SonarLint | Análisis en IDE | Gratuito |
| Semgrep | SAST local (pre-commit + Makefile) | Gratuito (OSS) |
| detect-secrets | Detección de secretos local | Gratuito (OSS) |
| pre-commit | Hooks de Git | Gratuito (OSS) |
| SonarCloud | Quality Gate en PR | Gratuito (repos públicos) |
| CodeQL | SAST en PR | Gratuito (público y privado) |
| Trivy | SCA en PR + CI/CD | Gratuito (OSS) |
| Gitleaks | Detección de secretos en PR | Gratuito (repos públicos) |
| GitHub Actions | CI/CD runner | Gratuito (2000 min/mes privado, ilimitado público) |
| GitHub Advanced Security | Secret scanning + push protection | Gratuito (repos públicos) |
| Render | Hosting del servicio | Gratuito (free tier) |
| MongoDB Atlas | Base de datos | Gratuito (free tier M0) |

---

## Verificación de instalación

```bash
# Verificar hooks instalados
pre-commit run --all-files

# Ejecutar todas las validaciones locales (lint + build + test + sast + secrets)
make check
```

---

## Solución de problemas frecuentes

**Semgrep no encuentra reglas o falla en el pre-commit hook**
→ Verifica la instalación: `semgrep --version`. Si falla, reinstala con `pip install semgrep`.

**SonarCloud Quality Gate siempre falla**
→ Revisa el umbral de cobertura configurado en sonarcloud.io para tu proyecto.

**Trivy bloquea por CVE sin fix disponible**
→ Edita `.trivyignore` en la raíz del proyecto para ignorar CVEs conocidos y aceptados:
```
# .trivyignore
CVE-2023-XXXX   # Descripción del por qué se acepta este riesgo
```

**Render deploy falla con "Build failed"**
→ Verifica que `mvn -B clean package -DskipTests` funciona localmente.
→ Revisa los logs de build en el dashboard de Render.

**Cold start lento en Render Free**
→ Es normal (~30s). Para proyectos de producción real, considera el plan Starter ($7/mes).
