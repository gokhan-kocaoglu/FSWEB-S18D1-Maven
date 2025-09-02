package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 =  """
        SELECT o.*
        FROM public.ogrenci o
        WHERE EXISTS (
            SELECT 1
            FROM public.islem i
            WHERE i.ogrno = o.ogrno
        )
        """;
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = """
        SELECT o.*
        FROM public.ogrenci o
        WHERE NOT EXISTS (
            SELECT 1
            FROM public.islem i
            WHERE i.ogrno = o.ogrno
        )
        """;
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = """
        SELECT o.sinif AS sinif,
               COUNT(i.kitapno) AS count
        FROM public.ogrenci o
        LEFT JOIN public.islem i ON i.ogrno = o.ogrno
        WHERE o.sinif IN ('10A','10B')
        GROUP BY o.sinif
        ORDER BY o.sinif
        """;
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = """
        SELECT COUNT(*) FROM public.ogrenci
        """;
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = """
        SELECT COUNT(DISTINCT o.ad)
        FROM public.ogrenci o
        """;
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = """
        SELECT o.ad AS ad,
               COUNT(*) AS count
        FROM public.ogrenci o
        GROUP BY o.ad
        ORDER BY o.ad
        """;
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = """
        SELECT o.sinif AS sinif,
               COUNT(*) AS count
        FROM public.ogrenci o
        GROUP BY o.sinif
        """;
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = """
        SELECT o.ad   AS ad,
               o.soyad AS soyad,
               COUNT(i.kitapno) AS count
        FROM public.ogrenci o
        INNER JOIN public.islem i ON i.ogrno = o.ogrno
        GROUP BY o.ad, o.soyad
        """;
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}
