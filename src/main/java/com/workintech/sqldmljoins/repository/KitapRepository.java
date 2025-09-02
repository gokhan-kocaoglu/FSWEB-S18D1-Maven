package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitapRepository extends JpaRepository<Kitap, Long> {

    //Dram ve Hikaye türündeki kitapları listeleyin. JOIN kullanmadan yapın.
    String QUESTION_1 = """
        SELECT *
        FROM public.kitap k
        WHERE k.turno IN (
            SELECT t.turno
            FROM public.tur t
            WHERE t.ad IN ('Dram', 'Hikaye')
        )
        """;
    @Query(value = QUESTION_1, nativeQuery = true)
    List<Kitap> findBooks();


    String QUESTION_10 = """
        SELECT AVG(k.puan)
        FROM public.kitap k
        """;
    @Query(value = QUESTION_10, nativeQuery = true)
    Double findAvgPointOfBooks();


}
