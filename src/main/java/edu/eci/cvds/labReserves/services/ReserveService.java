package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.dto.ReserveRequest;
import edu.eci.cvds.labReserves.repository.mongodb.ReserveMongoRepository;
import edu.eci.cvds.labReserves.repository.mongodb.ScheduleMongoRepository;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.Schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides business logic for managing reserves within the application.
 */
@Service
public class ReserveService {

    /** Constant for random year. */
    private static final int DEFAULT_YEAR = 2026;
    /** Upper bound for random user ID. */
    private static final int MAX_USER_ID = 1000007948;
    /** Lower bound for random user ID. */
    private static final int MIN_USER_ID = 1000000407;
    /** Max days in a month for random gen. */
    private static final int MAX_DAYS = 31;
    /** Max months. */
    private static final int MAX_MONTHS = 12;
    /** Days in a week. */
    private static final int DAYS_IN_WEEK = 7;

    /** interface of Reserve. */
    private final ReserveMongoRepository reserveRepo;

    /** interface of Schedule. */
    private final ScheduleMongoRepository scheduleRepo;

    /**
     * Constructor for ReserveService.
     *
     * @param pReserveRepo  repository for reserves.
     * @param pScheduleRepo repository for schedules.
     */
    public ReserveService(final ReserveMongoRepository pReserveRepo,
            final ScheduleMongoRepository pScheduleRepo) {
        this.reserveRepo = pReserveRepo;
        this.scheduleRepo = pScheduleRepo;
    }

    /**
     * Saves a reserve.
     *
     * @param pReq the reserve made of the user.
     * @return the saved Reserve object.
     * @throws LabReserveException if reserve already exists.
     */
    public final ReserveMongodb saveReserve(final ReserveRequest pReq)
            throws LabReserveException {
        Schedule schedule = new Schedule(pReq.getStartHour(),
                pReq.getNumberDay(), pReq.getDay(), pReq.getMonth(),
                pReq.getYear(), pReq.getLaboratoryName());
        ScheduleMongodb scheduleMongodb = new ScheduleMongodb(schedule);
        Reserve reserve = new Reserve(pReq.getType(),
                pReq.getReason(), pReq.getUserId());
        reserve.setSchedule(scheduleMongodb.getId());
        ReserveMongodb resMongo = new ReserveMongodb(reserve);
        if (!anotherReserve(scheduleMongodb)) {
            throw new LabReserveException(
                    LabReserveException.RESERVE_ALREADY_EXIST);
        }
        scheduleRepo.save(scheduleMongodb);
        reserveRepo.save(resMongo);
        return resMongo;
    }

    /**
     * Deletes a reservation by its associated schedule ID.
     *
     * @param pScheduleId The schedule ID linked to the reservation.
     * @throws LabReserveException If the reservation cannot be found.
     */
    public final void deleteReserveByScheduleId(final String pScheduleId)
            throws LabReserveException {
        ReserveMongodb resMongo = reserveRepo.findByScheduleId(pScheduleId);
        reserveRepo.deleteById(resMongo.getId());
        ResponseEntity.noContent().build();
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param pScheduleId The ID of the schedule to delete.
     */
    public final void deleteById(final String pScheduleId) {
        scheduleRepo.deleteById(pScheduleId);
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param pId The reservation ID to delete.
     * @throws LabReserveException If the reservation cannot be found.
     */
    public final void deleteReserveById(final String pId)
            throws LabReserveException {
        reserveRepo.deleteById(pId);
        ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all reservations.
     *
     * @return A list of ReserveRequest objects.
     */
    public final List<ReserveRequest> getAllReserves() {
        List<ReserveMongodb> reserve = reserveRepo.findAll();
        List<ReserveRequest> reserveRequests = new ArrayList<>();
        for (ReserveMongodb resMongo : reserve) {
            ScheduleMongodb schMongo = scheduleRepo
                    .findByScheduleId(resMongo.getSchedule());
            ReserveRequest request = new ReserveRequest(resMongo, schMongo);
            reserveRequests.add(request);
        }
        return reserveRequests;
    }

    /**
     * Retrieves reservations by laboratory abbreviation.
     *
     * @param pLabAbbreviation abbreviation of the lab.
     * @return list of requests.
     */
    public final List<ReserveRequest> getReserveByLaboratory(
            final String pLabAbbreviation) {
        List<ReserveRequest> labReserves = new ArrayList<>();
        List<ReserveMongodb> reserves = reserveRepo.findAll();
        for (ReserveMongodb reserveMongodb : reserves) {
            String scheduleId = reserveMongodb.getSchedule();
            ScheduleMongodb scheduleMongodb = scheduleRepo
                    .findByScheduleId(scheduleId);
            String abbreviation = scheduleMongodb.getLaboratory();
            if (abbreviation.equals(pLabAbbreviation)) {
                ReserveRequest request = new ReserveRequest(reserveMongodb,
                        scheduleMongodb);
                labReserves.add(request);
            }
        }
        return labReserves;
    }

    /**
     * Retrieves reservations by user ID.
     *
     * @param pUserId the ID of the user.
     * @return list of requests.
     */
    public final List<ReserveRequest> getReserveByUser(final int pUserId) {
        List<ReserveMongodb> reserves = reserveRepo.findByUserId(pUserId);
        List<ReserveRequest> reserveRequests = new ArrayList<>();
        for (ReserveMongodb reserveMongodb : reserves) {
            String scheduleId = reserveMongodb.getSchedule();
            ScheduleMongodb scheduleMongodb = scheduleRepo
                    .findByScheduleId(scheduleId);
            ReserveRequest request = new ReserveRequest(reserveMongodb,
                    scheduleMongodb);
            reserveRequests.add(request);
        }
        return reserveRequests;
    }

    /**
     * Retrieves reservations by day of the week.
     *
     * @param pDay the day.
     * @return list of requests.
     */
    public final List<ReserveRequest> getReserveByDay(final DayOfWeek pDay) {
        List<ReserveRequest> labReserves = new ArrayList<>();
        List<ReserveMongodb> reserves = reserveRepo.findAll();
        for (ReserveMongodb res : reserves) {
            ScheduleMongodb sch = scheduleRepo
                    .findByScheduleId(res.getSchedule());
            if (pDay.equals(sch.getDay())) {
                labReserves.add(new ReserveRequest(res, sch));
            }
        }
        return labReserves;
    }

    /**
     * Retrieves reservations by month.
     *
     * @param pMonth the month.
     * @return list of mongo reserves.
     */
    public final List<ReserveMongodb> getReserveByMonth(final Month pMonth) {
        List<ReserveMongodb> labReserves = new ArrayList<>();
        List<ReserveMongodb> reserves = reserveRepo.findAll();
        for (ReserveMongodb res : reserves) {
            ScheduleMongodb sch = scheduleRepo
                    .findByScheduleId(res.getSchedule());
            if (pMonth.equals(sch.getMonth())) {
                labReserves.add(res);
            }
        }
        return labReserves;
    }

    /**
     * Retrieves a list of reservations by its user ID.
     *
     * @param pUserId the user id.
     * @return list of reserves.
     */
    public final List<ReserveMongodb> getReserveByUserId(final int pUserId) {
        return reserveRepo.getAllByUserId(pUserId);
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param pId reservation id.
     * @return the request.
     */
    public final ReserveRequest getReserveById(final String pId) {
        ReserveMongodb res = reserveRepo.findByReserveId(pId);
        ScheduleMongodb sch = scheduleRepo.findByScheduleId(res.getSchedule());
        return new ReserveRequest(res, sch);
    }

    /**
     * Gets only the reserve entity.
     *
     * @param pId reservation id.
     * @return mongo entity.
     */
    public final ReserveMongodb getOnlyReserveById(final String pId) {
        return reserveRepo.findByReserveId(pId);
    }

    /**
     * Retrieves a schedule by its ID.
     *
     * @param pId schedule id.
     * @return schedule mongo entity.
     */
    public final ScheduleMongodb getScheduleById(final String pId) {
        return scheduleRepo.findByScheduleId(pId);
    }

    /**
     * Retrieves a schedule based on its details.
     *
     * @param pSch schedule object.
     * @return found schedule.
     */
    public final ScheduleMongodb getScheduleBySchedule(final Schedule pSch) {
        return scheduleRepo.findByTime(pSch.getStartHour(), pSch.getNumberDay(),
                pSch.getDay(), pSch.getMonth(), pSch.getYear(),
                pSch.getLaboratory());
    }

    /**
     * Checks if another reserve exists for the same slot.
     *
     * @param pSch schedule to check.
     * @return true if slot is free.
     */
    private boolean anotherReserve(final ScheduleMongodb pSch) {
        List<ScheduleMongodb> schedules = scheduleRepo.findAll();
        for (ScheduleMongodb sch : schedules) {
            boolean sameLab = sch.getLaboratory().equals(pSch.getLaboratory());
            boolean sameTime = sch.getYear() == pSch.getYear()
                    && sch.getMonth().equals(pSch.getMonth())
                    && sch.getDay().equals(pSch.getDay())
                    && sch.getNumberDay() == pSch.getNumberDay()
                    && sch.getStartHour().equals(pSch.getStartHour());
            if (sameLab && sameTime) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates random reserves for testing.
     *
     * @return list of generated requests.
     * @throws LabReserveException if generation fails.
     */
    public final List<ReserveRequest> generateRandomReserves()
            throws LabReserveException {
        Random random = new Random();
        final int minRand = 100;
        final int maxRand = 1001;
        int numReserve = random.nextInt(minRand, maxRand);
        String[] typeRes = {"lesson", "available"};
        String[] reasonRes = {"AYPR", "AYED", "CVDS", "POOB", "CNYT",
                "IETI", "ARSW", "MBDA", "ACSO", "RECO", "SPTI", "TSOR",
                "AUPN", "AREP"};
        String[] labRes = {"LABISW", "LABICO", "LABPLA", "EDEI",
                "LABISIS", "EDFI"};

        final int h7 = 7;
        final int h8 = 8;
        final int h10 = 10;
        final int h11 = 11;
        final int h13 = 13;
        final int h14 = 14;
        final int h16 = 16;
        final int h17 = 17;
        final int min30 = 30;

        LocalTime[] possibleTimes = {
                LocalTime.of(h7, 0), LocalTime.of(h8, min30),
                LocalTime.of(h10, 0), LocalTime.of(h11, min30),
                LocalTime.of(h13, 0), LocalTime.of(h14, min30),
                LocalTime.of(h16, 0), LocalTime.of(h17, min30)
        };

        List<ReserveRequest> reserveList = new ArrayList<>();
        final int priorityMax = 6;

        for (int i = 0; i < numReserve; i++) {
            int priority = random.nextInt(1, priorityMax);
            int userId = random.nextInt(MIN_USER_ID, MAX_USER_ID);
            int numDay = random.nextInt(1, MAX_DAYS);
            DayOfWeek day = DayOfWeek.of(random.nextInt(1, DAYS_IN_WEEK + 1));
            Month month = Month.of(random.nextInt(1, MAX_MONTHS + 1));
            String type = typeRes[random.nextInt(typeRes.length)];
            String reason = reasonRes[random.nextInt(reasonRes.length)];
            String lab = labRes[random.nextInt(labRes.length)];
            LocalTime time =
                    possibleTimes[random.nextInt(possibleTimes.length)];
            Schedule sch = new Schedule(
                    time,
                    numDay,
                    day,
                    month,
                    DEFAULT_YEAR,
                    lab);
            ScheduleMongodb schMongo = new ScheduleMongodb(sch);
            Reserve res = new Reserve(type, reason, userId);
            res.setSchedule(schMongo.getId());
            ReserveMongodb resMongo = new ReserveMongodb(res);
            resMongo.setPriority(priority);

            if (anotherReserve(schMongo)) {
                scheduleRepo.save(schMongo);
                reserveRepo.save(resMongo);
                reserveList.add(new ReserveRequest(resMongo, schMongo));
            }
        }
        return reserveList;
    }
}
