import React from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import Modal from "../Modal";
import { fetchBook, deleteBook } from "../../actions";
import history from "../../history";

class BookDelete extends React.Component {
  componentDidMount() {
    this.props.fetchBook(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;
    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteBook(id)}
          className="ui button negative"
        >
          Deletar
        </button>
        <Link to="/" className="ui button">
          Cancelar
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.book) {
      return "Você quer mesmo deletar esse livro?";
    }
    return `Você quer mesmo deletar o livro de título: "${this.props.book.title}"?`;
  }

  render() {
    return (
      <Modal
        title="Deletar livro"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push("/")}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { book: state.books[ownProps.match.params.id] };
};

export default connect(mapStateToProps, { fetchBook, deleteBook })(
  BookDelete
);
