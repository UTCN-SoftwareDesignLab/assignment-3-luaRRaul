package com.stockhelt.backend;

import com.stockhelt.backend.appointment.dto.AppointmentMinimalDTO;
import com.stockhelt.backend.patient.dto.PatientMinimalDTO;
import com.stockhelt.backend.user.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        }else if(cls.equals(PatientMinimalDTO.class)){
            supplier = TestCreationFactory::newPatientMinimalDTO;
        }else if(cls.equals(AppointmentMinimalDTO.class)){
            supplier = TestCreationFactory::newAppointmentMinimalDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static AppointmentMinimalDTO newAppointmentMinimalDTO() {
        return AppointmentMinimalDTO.builder()
                .id(randomLong())
                .doctorName(randomString())
                .patientName(randomEmail())
                .date(randomDate())
                .build();
    }

    private static Date randomDate() {
        return new java.util.Date();
    }

    private static PatientMinimalDTO newPatientMinimalDTO() {
        return PatientMinimalDTO.builder()
                .id(randomLong())
                .firstName(randomString())
                .lastName(randomString())
                .CNP(randomString())
                .build();
    }

    private static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static float randomFloat() {
        return new Random().nextFloat();
    }

    public static int randomInt() {
        return new Random().nextInt();
    }
}
