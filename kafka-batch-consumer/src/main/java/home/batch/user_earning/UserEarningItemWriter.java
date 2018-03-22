package home.batch.user_earning;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserEarningItemWriter implements ItemWriter<UserEarningEntity>{
    @Autowired
    private UserEarningRepository repository;
    @Override
    public void write(List<? extends UserEarningEntity> items) throws Exception {
        repository.saveAll(items);
    }
}
