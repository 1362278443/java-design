package com.design.dao;

import com.design.domain.Book;
import com.design.domain.Info;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface InfoDao {

    @Select("SELECT t.date,coalesce(u.number,0) 'number' from(SELECT subdate(CURRENT_DATE, numlist.id) AS 'date' FROM (SELECT DISTINCT x.i + y.i * 10 + z.i * 100 AS id FROM num x,num y,num z ORDER BY id) AS numlist WHERE subdate(CURRENT_DATE, numlist.id) > date_sub(CURRENT_DATE,interval 1 year)) t LEFT JOIN (SELECT DATE(Borrow.borrow_time)as date,count(1) number FROM Borrow GROUP BY DATE(Borrow.borrow_time)) u on t.date = u.date ORDER BY t.date")
    public List<Info> getAllBookDataBorrow();
    @Select("SELECT t.date,coalesce(u.number,0) 'number' from(SELECT subdate(CURRENT_DATE, numlist.id) AS 'date' FROM (SELECT DISTINCT x.i + y.i * 10 + z.i * 100 AS id FROM num x,num y,num z ORDER BY id) AS numlist WHERE subdate(CURRENT_DATE, numlist.id) > date_sub(CURRENT_DATE,interval 1 year)) t LEFT JOIN (SELECT DATE(Borrow.borrow_time)as date,count(1) number FROM Borrow,Book_info where name=#{name} and pub=#{pub} and Book_info.id=Borrow.id GROUP BY DATE(Borrow.borrow_time)) u on t.date = u.date ORDER BY t.date")
    public List<Info> getOneBookDataBorrow(@Param("name")String name, @Param("pub")String pub);

    @Select("SELECT t.date,coalesce(u.number,0) 'number' from(SELECT subdate(CURRENT_DATE, numlist.id) AS 'date' FROM (SELECT DISTINCT x.i + y.i * 10 + z.i * 100 AS id FROM num x,num y,num z ORDER BY id) AS numlist WHERE subdate(CURRENT_DATE, numlist.id) > date_sub(CURRENT_DATE,interval 1 year)) t LEFT JOIN (SELECT DATE(Borrow.borrow_time)as date,count(DISTINCT sno) number FROM Borrow GROUP BY DATE(Borrow.borrow_time)) u on t.date = u.date ORDER BY t.date")
    public List<Info> getAllStuDataBorrow();

    @Select("SELECT\n" +
            "    @rownum := @rownum + 1 AS rank,obj.name,obj.num\n" +
            "FROM\n" +
            "    (\n" +
            "        SELECT\n" +
            "            t.name,\n" +
            "            t.num\n" +
            "        FROM\n" +
            "            (SELECT Book_info.name,count(*) num from Borrow,Book_info where DATE(borrow_time)>DATE_SUB(CURDATE(), INTERVAL 1 YEAR) and Book_info.id=Borrow.id GROUP BY Book_info.name,Book_info.pub\n" +
            ",Book_info.time) t\n" +
            "        ORDER BY\n" +
            "            t.num DESC\n" +
            "    ) AS obj,\n" +
            "    (SELECT @rownum := 0) r")
    public List<Info.BookRank> getRankBookBorrow();

}
