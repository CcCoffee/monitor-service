import React from 'react';
import Pagination from 'react-bootstrap/Pagination';

function MyPagination({ currentPage, totalPages, onPageChange }) {

  const isPreviousDisabled = currentPage === 1;
  const isNextDisabled = currentPage === totalPages;

  // 根据当前页和总页数生成页码数组
  const getPageNumbers = () => {
    const pageNumbers = [];

    // 显示当前页的前两页和后两页
    const minPage = Math.max(1, currentPage - 2);
    const maxPage = Math.min(totalPages, currentPage + 2);

    for (let i = minPage; i <= maxPage; i++) {
      pageNumbers.push(i);
    }

    return pageNumbers;
  };

  return (
    <Pagination>
      <Pagination.First onClick={() => onPageChange(1)} disabled={isPreviousDisabled}/>
      <Pagination.Prev
        onClick={() => onPageChange(Math.max(1, currentPage - 1))}
        disabled={isPreviousDisabled}
      />

      {getPageNumbers().map((pageNumber) => (
        <Pagination.Item
          key={pageNumber}
          active={pageNumber === currentPage}
          onClick={() => onPageChange(pageNumber)}
        >
          {pageNumber}
        </Pagination.Item>
      ))}

      <Pagination.Next
        onClick={() => onPageChange(Math.min(totalPages, currentPage + 1))}
        disabled={isNextDisabled}
      />
      <Pagination.Last onClick={() => onPageChange(totalPages)} disabled={isNextDisabled}/>
    </Pagination>
  );
}

export default MyPagination;