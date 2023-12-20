package ru.homework.cdrtest.abonent;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.billingrealtime.Action;
import ru.homework.cdrtest.billingrealtime.BillingRealTimeService;
import ru.homework.cdrtest.callrecord.CallRecordDTO;
import ru.homework.cdrtest.callrecord.CallRecordMapper;
import ru.homework.cdrtest.callrecord.CallRecordReportDTO;
import ru.homework.cdrtest.callrecord.CallRecordRepository;
import ru.homework.cdrtest.role.RoleEntity;
import ru.homework.cdrtest.role.Role;
import ru.homework.cdrtest.role.RoleRepository;
import ru.homework.cdrtest.tarifftype.TariffTypeEntity;
import ru.homework.cdrtest.tarifftype.TariffTypeRepository;

import java.util.List;
import java.util.Set;

import static ru.homework.cdrtest.utils.AuthUtils.isAdmin;
import static ru.homework.cdrtest.utils.DateUtils.getCurrentTime;

@Service
@RequiredArgsConstructor
public class AbonentServiceImpl implements AbonentService {
    private final AbonentRepository abonentRepository;
    private final TariffTypeRepository tariffTypeRepository;
    private final AbonentMapper mapper;
    private final CallRecordRepository callRecordRepository;
    private final CallRecordMapper callRecordMapper;
    private final BillingRealTimeService billingRealTimeService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public AbonentDTO save(AbonentCreateDTO dto) throws Exception {
        if (!isAdmin()) {
            throw new IllegalAccessException("Нет доступа");
        }
        if (abonentRepository.findAbonentByPhoneNumber(dto.getPhoneNumber()).isPresent()) {
            throw new Exception("Пользователь с таким номером телефона уже существует");
        }
        var entity = mapper.toEntity(dto)
                .setTariffType(getTariffType(dto.getTariffTypeId()))
                .setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRoleEntities(getRole(dto.getRole()));
        entity = abonentRepository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public AbonentDTO pay(AbonentPayDTO dto) {
        var abonent = getAbonentByPhoneNumber(dto.getNumberPhone());
        abonent.setBalance(abonent.getBalance() + dto.getMoney());
        return mapper.toDto(abonent);
    }

    @Override
    public CallRecordReportDTO report(String phoneNumber) throws IllegalAccessException {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(phoneNumber)) {
            throw new IllegalAccessException("Нет доступа");
        }
        var abonent = getAbonentByPhoneNumber(phoneNumber);
        var callRecords = callRecordRepository.findAllByPhoneNumberCurrentMonth(phoneNumber, getCurrentTime()).stream()
                .sorted()
                .map(callRecordMapper::toDto)
                .toList();
        var totalCost = callRecords.stream()
                .mapToDouble(CallRecordDTO::getCost)
                .sum();
        return new CallRecordReportDTO()
                .setId(abonent.getId())
                .setPhoneNumber(abonent.getPhoneNumber())
                .setTariffType(abonent.getTariffType().getTariffType())
                .setCallRecords(callRecords)
                .setTotalCost(totalCost)
                .setMonetaryUnit("RUB");
    }

    @Override
    @Transactional
    public AbonentDTO changeTariffType(AbonentTariffDTO dto) throws IllegalAccessException {
        if (!isAdmin()) {
            throw new IllegalAccessException("Нет доступа");
        }
        var abonent = getAbonentByPhoneNumber(dto.getNumberPhone())
                .setTariffType(getTariffType(dto.getTariffTypeId()));
        return mapper.toDto(abonent);
    }

    @Override
    @Transactional
    public List<AbonentShortDTO> billing(Action action) throws IllegalAccessException {
        if (!isAdmin()) {
            throw new IllegalAccessException("Нет доступа");
        }
        if (action == Action.RUN) {
            var abonents = abonentRepository.findAllWithPositiveBalance();
            billingRealTimeService.performBilling(abonents);
            return abonents.stream()
                    .map(mapper::toShortDto)
                    .toList();
        }
        return List.of();
    }

    private TariffTypeEntity getTariffType(Long id) {
        return tariffTypeRepository.findTariffTypeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тариф не найден"));
    }

    private AbonentEntity getAbonentByPhoneNumber(String phoneNumber) {
        return abonentRepository.findAbonentByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Абонент не найден"));
    }

    private Set<RoleEntity> getRole(Set<Role> roles) {
        return roleRepository.findByRole(roles);
    }
}
