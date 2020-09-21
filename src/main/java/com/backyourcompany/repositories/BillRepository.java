package com.backyourcompany.repositories;

import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findAllByCard(Card card);
}
