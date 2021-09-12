import React from "react";
import { connect } from "react-redux";
import { fetchBook } from "../../actions";

class BookShow extends React.Component {
  componentDidMount() {
    this.props.fetchBook(this.props.match.params.id);
  }

  render() {
    if (!this.props.book) {
      return <div>Carregando...</div>;
    }
    const { title, author, status } = this.props.book;
    return (
      <div>
        <h1>{title}</h1>
        <h5>{author}</h5>
        <h5>{status}</h5>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { book: state.books[ownProps.match.params.id] };
};

export default connect(mapStateToProps, { fetchBook })(BookShow);
