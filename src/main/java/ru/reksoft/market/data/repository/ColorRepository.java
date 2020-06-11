package ru.reksoft.market.data.repository;

import ru.reksoft.market.data.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    @Override
    List<Color> findAll();
}
