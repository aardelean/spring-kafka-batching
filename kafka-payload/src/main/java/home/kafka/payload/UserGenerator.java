package home.kafka.payload;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserGenerator {
    private AtomicLong idGenerator = new AtomicLong(0);

    public User generateDefault() {
        return User
                .builder()
                .id(idGenerator.incrementAndGet())
                .uuid(UUID.randomUUID().toString())
                .age(33)
                .firstName("Marcin")
                .lastName("Dobrovolsky")
                .weight(86)
                .address(Address
                        .builder()
                        .street("SomeStreet 62")
                        .city("Muenchen")
                        .country("Germany")
                        .plz(81379)
                        .build())
                .workingJobs(List.of(
                        WorkingJob
                                .builder()
                                .company(Company
                                        .builder()
                                        .address(Address
                                                .builder()
                                                .plz(80010)
                                                .country("Germany")
                                                .city("Muenchen")
                                                .street("Street 33")
                                                .build())
                                        .name("Linker")
                                        .registrationDate(Date
                                                .from(LocalDate
                                                        .of(2007, 11, 1)
                                                        .atStartOfDay(ZoneId.systemDefault())
                                                        .toInstant()))
                                        .build())
                                .salary("60000")
                                .startDate(Date
                                        .from(LocalDate
                                                .of(2011, 11, 14)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .endDate(Date
                                        .from(LocalDate
                                                .of(2015, 7, 14)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .build(),
                        WorkingJob
                                .builder()
                                .company(Company
                                        .builder()
                                        .address(Address
                                                .builder()
                                                .plz(80101)
                                                .country("Germany")
                                                .city("Muenchen")
                                                .street("Street 24")
                                                .build())
                                        .name("Applicable")
                                        .registrationDate(Date
                                                .from(LocalDate
                                                        .of(2005, 6, 1)
                                                        .atStartOfDay(ZoneId.systemDefault())
                                                        .toInstant()))
                                        .build())
                                .salary("66000")
                                .startDate(Date
                                        .from(LocalDate
                                                .of(2015, 7, 16)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .endDate(Date
                                        .from(LocalDate
                                                .of(2016, 2, 6)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .build(),
                        WorkingJob
                                .builder()
                                .company(Company
                                        .builder()
                                        .address(Address
                                                .builder()
                                                .plz(13476)
                                                .country("USA")
                                                .city("San Francisco")
                                                .street("unkown")
                                                .build())
                                        .name("Facebook")
                                        .registrationDate(Date
                                                .from(LocalDate
                                                        .of(2009, 1, 5)
                                                        .atStartOfDay(ZoneId.systemDefault())
                                                        .toInstant()))
                                        .build())
                                .salary("73000")
                                .startDate(Date
                                        .from(LocalDate
                                                .of(2016, 2, 8)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .endDate(Date
                                        .from(LocalDate
                                                .of(2018, 2, 2)
                                                .atStartOfDay(ZoneId.systemDefault())
                                                .toInstant()))
                                .build()
                ))
                .build();
    }
}
