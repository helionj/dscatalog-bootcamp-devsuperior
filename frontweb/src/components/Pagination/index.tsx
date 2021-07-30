import ReactPaginate from 'react-paginate';
import { ReactComponent as ArrowIcon } from '../../assets/images/arrowIcon.svg';
import './styles.css';
const Pagination = () => {
  return (
    <ReactPaginate
      pageCount={10}
      pageRangeDisplayed={3}
      marginPagesDisplayed={1}
      containerClassName="pagination-container"
      pageLinkClassName="pagination-item"
      breakClassName="pagination-item"
      previousLabel={<ArrowIcon />}
      nextLabel={<ArrowIcon />}
      previousClassName="arrow-previous"
      nextClassName="arrow-next"
      activeLinkClassName="paginate-link-active"
      disabledClassName="arrow-inactive"
    />
  );
};
export default Pagination;
