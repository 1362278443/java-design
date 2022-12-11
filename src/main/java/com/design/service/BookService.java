package com.design.service;

import com.design.domain.Book;

import java.util.List;

public interface BookService {
    /**
     * 查询所有书籍记录
     * @return
     */
    public List<Book> getAll();

    /**
     * 按照ID来查找书籍
     * @param id
     * @return
     */
    public Book getById(Integer id);

    /**
     * 提供Book数据后，执行添加到数据库
     * @param book
     * @return
     */
    public boolean insertBook(Book book);

    /**
     * 更新数据库中该Book数据
     * @param book
     * @return
     */
    public boolean updateBook(Book book);

    /**
     * 按照ID来删除该书籍
     * @param id
     * @return
     */
    public boolean deleteById(Integer id);
}
