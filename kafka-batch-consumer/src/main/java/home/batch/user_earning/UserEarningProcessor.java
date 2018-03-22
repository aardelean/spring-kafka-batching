package home.batch.user_earning;

import home.kafka.payload.user_earning.UserEarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserEarningProcessor implements ItemProcessor<UserEarning, UserEarningEntity> {
    @Override
    public UserEarningEntity process(UserEarning item) throws Exception {
        UserEarningEntity result = null;
        log.info("processing one item");
        try {
            result = UserEarningEntity
                    .builder()
                    .fullName(item.getFirstName() + " " + item.getLastName())
                    .salary(item.getTotalEarned())
                    .uuid(item.getUuid())
                    .build();
           long countDigits = result.getUuid().chars().mapToObj(c -> (char) c).filter(c -> Character.isDigit(c)).count();
           log.info("found " +  countDigits + " digits");
           Thread.sleep(countDigits * 20);
        } catch (InterruptedException e) {
            log.error("processor sleep interrupted");
        }
        return result;
    }
}
