package com.icsdetec.aprendendospring.infrastruture.repository;

import com.icsdetec.aprendendospring.infrastruture.entity.Telefone;
import com.icsdetec.aprendendospring.infrastruture.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
