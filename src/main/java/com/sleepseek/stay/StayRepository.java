package com.sleepseek.stay;

import com.sleepseek.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface StayRepository extends JpaRepository<Stay, Long> {
    Page<Stay> findAllByUserOrderByName(User username, Pageable pageable);

    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT JOIN users u ON s.user_id = u.id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ORDER BY s.name ASC"
            , nativeQuery = true)
    Page<Stay> findAllByNameAsc(@Param("priceFrom") Long priceFrom,
                                @Param("priceToo") Long priceToo,
                                @Param("username") String username,
                                @Param("category") String category,
                                @Param("city") String city,
                                @Param("longitude") Double longitude,
                                @Param("latitude") Double latitude,
                                @Param("maxDistance") Double maxDistance,
                                Pageable pageable);

    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT JOIN users u ON s.user_id = u.id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ORDER BY s.name DESC"
            , nativeQuery = true)
    Page<Stay> findAllByNameDesc(@Param("priceFrom") Long priceFrom,
                                 @Param("priceToo") Long priceToo,
                                 @Param("username") String username,
                                 @Param("category") String category,
                                 @Param("city") String city,
                                 @Param("longitude") Double longitude,
                                 @Param("latitude") Double latitude,
                                 @Param("maxDistance") Double maxDistance,
                                 Pageable pageable);

    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT JOIN users u ON s.user_id = u.id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ORDER BY s.city DESC "
            , nativeQuery = true)
    Page<Stay> findAllByCityDesc(@Param("priceFrom") Long priceFrom,
                                 @Param("priceToo") Long priceToo,
                                 @Param("username") String username,
                                 @Param("category") String category,
                                 @Param("city") String city,
                                 @Param("longitude") Double longitude,
                                 @Param("latitude") Double latitude,
                                 @Param("maxDistance") Double maxDistance,
                                 Pageable pageable);

    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT JOIN users u ON s.user_id = u.id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ORDER BY s.city ASC "
            , nativeQuery = true)
    Page<Stay> findAllByCityAsc(@Param("priceFrom") Long priceFrom,
                                @Param("priceToo") Long priceToo,
                                @Param("username") String username,
                                @Param("category") String category,
                                @Param("city") String city,
                                @Param("longitude") Double longitude,
                                @Param("latitude") Double latitude,
                                @Param("maxDistance") Double maxDistance,
                                Pageable pageable);

    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT OUTER JOIN " +
                    "(SELECT stay_id, AVG(r.rating) avgRate FROM reviews r GROUP BY stay_id) res " +
                    "ON s.id = res.stay_id " +
                    "LEFT JOIN users u ON u.id = s.user_id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ORDER BY res.avgRate DESC ", nativeQuery = true)
    Page<Stay> findByAvgRateDesc(
            @Param("priceFrom") Long priceFrom,
            @Param("priceToo") Long priceToo,
            @Param("username") String username,
            @Param("category") String category,
            @Param("city") String city,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("maxDistance") Double maxDistance,
            Pageable pageable);


    @Query(value =
            "SELECT * " +
                    "FROM stays s LEFT OUTER JOIN " +
                    "(SELECT stay_id, AVG(r.rating) avgRate FROM reviews r GROUP BY stay_id) res " +
                    "LEFT JOIN users u ON u.id = s.user_id " +
                    "WHERE " +
                    "(:priceFrom IS NULL OR s.min_price > CAST(:priceFrom AS bigint)) AND (:priceToo IS NULL OR s.min_price < CAST(:priceTo AS bigint))" +
                    "AND (:username IS NULL OR u.username LIKE :username) " +
                    "AND (:category IS NULL OR s.category LIKE :category) " +
                    "AND (:city IS NULL OR s.city LIKE :city) " +
                    "AND (:longitude IS NULL OR TRUE) " +
                    "AND (:latitude IS NULL OR TRUE) " +
                    "AND (:maxDistance IS NULL OR TRUE)  " +
                    "ON s.id = res.stay_id ORDER BY res.avgRate ASC ", nativeQuery = true)
    Page<Stay> findByAvgRateAsc(
            @Param("priceFrom") Long priceFrom,
            @Param("priceToo") Long priceToo,
            @Param("username") String username,
            @Param("category") String category,
            @Param("city") String city,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("maxDistance") Double maxDistance,
            Pageable pageable);
}
