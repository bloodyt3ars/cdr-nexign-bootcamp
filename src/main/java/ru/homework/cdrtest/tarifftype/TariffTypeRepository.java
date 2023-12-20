package ru.homework.cdrtest.tarifftype;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TariffTypeRepository extends JpaRepository<TariffTypeEntity, Long> {
    @Query("select t from TariffTypeEntity t where t.id = :id")
    Optional<TariffTypeEntity> findTariffTypeById(@NotNull Long id);
    @Query("select t from TariffTypeEntity t where t.tariffType = :type")
    Optional<TariffTypeEntity> findByTariffType(@NotNull TariffType type);
}
