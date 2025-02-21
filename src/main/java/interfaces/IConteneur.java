package interfaces;

import java.util.List;

public interface IConteneur <T> {
        void add(T t);

        List<T> getAll();

        void update(T t);

        void delete(String d);

    }

