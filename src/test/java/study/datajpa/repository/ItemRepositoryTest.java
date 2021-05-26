package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.entity.Item;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @Test
    void save() {
        Item item = new Item("1");
        itemRepository.save(item);
    }
}