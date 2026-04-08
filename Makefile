# =============================================================================
# Makefile — Pipeline Local de Seguridad (DevSecOps)
# Compatible con proyectos Java/Maven. Adaptable a otros stacks.
# Uso: make check   → ejecuta todos los controles antes del commit
# =============================================================================

.PHONY: help lint build test sast secrets check clean

# ---------------------------------------------------------------------------
# Variables configurables por el proyecto
# ---------------------------------------------------------------------------
PROJECT_NAME   ?= $(shell basename $(CURDIR))
SEVERITY_THRESHOLD ?= ERROR        # Umbral para Semgrep: INFO, WARNING, ERROR
JAVA_VERSION   ?= 17
COVERAGE_MIN   ?= 0               # Cobertura minima de pruebas (%)

# ---------------------------------------------------------------------------
# help — muestra los targets disponibles
# ---------------------------------------------------------------------------
help:
	@echo ""
	@echo "  Pipeline Local — DevSecOps"
	@echo "  -------------------------------------------"
	@echo "  make lint      Analisis de estilo con Checkstyle"
	@echo "  make build     Compilacion del proyecto"
	@echo "  make test      Pruebas unitarias + cobertura"
	@echo "  make sast      Analisis SAST con Semgrep (local)"
	@echo "  make secrets   Deteccion de secretos con detect-secrets"
	@echo "  make check     Validacion completa previa al commit"
	@echo "  make clean     Limpieza de artefactos"
	@echo ""

# ---------------------------------------------------------------------------
# lint — analisis de estilo de codigo con Checkstyle
# ---------------------------------------------------------------------------
lint:
	@echo "[1/5] Analisis de estilo (Checkstyle)..."
	@mvn checkstyle:check -q \
		|| (echo "X Lint fallo. Corrige los errores de estilo antes de continuar." && exit 1)
	@echo "OK Lint superado"

# ---------------------------------------------------------------------------
# build — compilacion del proyecto
# ---------------------------------------------------------------------------
build:
	@echo "[2/5] Compilando el proyecto..."
	@mvn compile -q \
		|| (echo "X Build fallo. Verifica errores de compilacion." && exit 1)
	@echo "OK Build exitoso"

# ---------------------------------------------------------------------------
# test — pruebas unitarias con reporte de cobertura (JaCoCo)
# ---------------------------------------------------------------------------
test:
	@echo "[3/5] Ejecutando pruebas unitarias..."
	@mvn test jacoco:report -q \
		|| (echo "X Pruebas fallidas. Revisa los reportes en target/site/jacoco/." && exit 1)
	@echo "OK Pruebas superadas"
	@# Verificar cobertura minima
	@COVERAGE=$$(python3 -c " \
		import xml.etree.ElementTree as ET; \
		tree = ET.parse('target/site/jacoco/jacoco.xml'); \
		root = tree.getroot(); \
		counters = {c.get('type'): c for c in root.findall('.//counter[@type]')}; \
		c = counters.get('INSTRUCTION'); \
		covered = int(c.get('covered',0)); missed = int(c.get('missed',0)); \
		total = covered + missed; \
		print(int(covered*100/total) if total else 0)" 2>/dev/null || echo "0"); \
	echo "  Cobertura actual: $$COVERAGE%  (minimo requerido: $(COVERAGE_MIN)%)"; \
	[ "$$COVERAGE" -ge "$(COVERAGE_MIN)" ] \
		|| (echo "X Cobertura insuficiente: $$COVERAGE% < $(COVERAGE_MIN)%" && exit 1)
	@echo "OK Cobertura OK"

# ---------------------------------------------------------------------------
# sast — analisis SAST local con Semgrep (gratuito, sin cuenta requerida)
# Requiere: pip install semgrep
# ---------------------------------------------------------------------------
sast:
	@echo "[4/5] Analisis SAST (Semgrep)..."
	@command -v semgrep >/dev/null 2>&1 \
		|| (echo "! Semgrep no encontrado. Instala con: pip install semgrep" && exit 1)
	@semgrep scan --config auto --severity ERROR --error \
		--exclude='target' --exclude='build' --exclude='node_modules' \
		. \
		|| (echo "X SAST encontro vulnerabilidades de severidad ERROR o superior." && exit 1)
	@echo "OK SAST superado"

# ---------------------------------------------------------------------------
# secrets — deteccion de secretos con detect-secrets
# Requiere: pip install detect-secrets
# ---------------------------------------------------------------------------
secrets:
	@echo "[5/5] Deteccion de secretos..."
	@command -v detect-secrets >/dev/null 2>&1 \
		|| (echo "! detect-secrets no encontrado. Instala con: pip install detect-secrets" && exit 1)
	@detect-secrets scan --baseline .secrets.baseline 2>/dev/null \
		|| detect-secrets scan > .secrets.baseline
	@detect-secrets audit .secrets.baseline --only-allowlisted \
		|| (echo "X Se detectaron posibles secretos en el codigo. Revisa .secrets.baseline" && exit 1)
	@echo "OK Sin secretos detectados"

# ---------------------------------------------------------------------------
# check — validacion completa previa al commit (target principal)
# ---------------------------------------------------------------------------
check: lint build test sast secrets
	@echo ""
	@echo "=========================================="
	@echo "  OK Todas las validaciones superadas."
	@echo "  Listo para commit y push a GitHub."
	@echo "=========================================="
	@echo ""

# ---------------------------------------------------------------------------
# clean — limpieza de artefactos de construccion
# ---------------------------------------------------------------------------
clean:
	@echo "Limpiando artefactos..."
	@mvn clean -q
	@echo "OK Limpieza completada"
