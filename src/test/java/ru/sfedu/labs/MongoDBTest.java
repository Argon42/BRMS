package ru.sfedu.labs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class MongoDBTest {
    private MongoDB mongoDB;
    private TestBean bean;

    @BeforeEach
    public void Setup() throws IOException {
        String database = MongoDBTest.class.getSimpleName();
        String url = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_URL);

        mongoDB = new MongoDB(url, database);

        bean = new TestBean("Roman", 91);
    }

    @AfterEach
    public void DropTable() {
        mongoDB.drop();
    }

    @Test
    public void serializeCorrectObject() throws JsonProcessingException {
        String json = MongoDB.objectToString(bean);
        Assertions.assertNotNull(json);
        TestBean newBean = MongoDB.stringToObject(json, TestBean.class);
        Assertions.assertEquals(bean, newBean);
    }

    @Test
    public void saveObjectToMongo() throws JsonProcessingException {
        mongoDB.saveObject(bean);
    }

    @Test
    public void saveNullToMongo() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> mongoDB.saveObject(null));
    }

    @Test
    public void deleteObjectFromMongo() throws JsonProcessingException {
        mongoDB.saveObject(bean);
        mongoDB.deleteObject(bean);
    }

    @Test
    public void deleteNotExistingObjectFromMongo() throws JsonProcessingException {
        mongoDB.saveObject(bean);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            mongoDB.deleteObject(new TestBean("Roma", 22));
        });
    }

    public static class TestBean implements Serializable {
        private String name;
        private int age;

        public TestBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public TestBean() {
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestBean testBean = (TestBean) o;
            return age == testBean.age && Objects.equals(name, testBean.name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
