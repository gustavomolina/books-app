import React from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import { fetchBooks } from "../../actions";

class BookList extends React.Component {
  componentDidMount() {
    this.props.fetchBooks();
  }

  renderAdmin(book) {
    return (
      <div className="right floated content">
        <Link to={`/books/edit/${book.id}`} className="ui button primary">
          Editar
          </Link>
        <Link
          to={`/books/delete/${book.id}`}
          className="ui button negative"
        >
          Deletar
          </Link>
      </div>
    );
  }

  renderWantReadBooksList() {
    const wantReadBooks = this.props.books
    .filter(book => book.status.value === 'QUERO_LER')

    return wantReadBooks.length != 0 ? wantReadBooks.map((book) => {return this.renderBook(book)}) : this.renderNoBooks()
  }

  renderReadedBooksList() {
    const readedBooks = this.props.books
    .filter(book => book.status.value === 'LIDO')

    return readedBooks.length != 0 ? readedBooks.map((book) => {return this.renderBook(book)}) : this.renderNoBooks()
  }

  renderReadingBooksList() {
    const readingBooks = this.props.books
    .filter(book => book.status.value === 'LENDO')

    return readingBooks.length != 0 ? readingBooks.map((book) => {return this.renderBook(book)}) : this.renderNoBooks()
  }

  renderBook(book) {
    return (
      <div className="item" key={book.id}>
        {this.renderAdmin(book)}
        <i className="large middle aligned icon book" />
        <div className="content">
          <Link className="header">
            <b>Título:</b> {book.title}
          </Link>
          <div className="author"><b>Autor:</b> {book.author}</div>
          <div className="status"><b>Status:</b> {book.status.label}</div>
          <div className="inclusionDate"><b>Data em que foi adicionado:</b> {book.inclusionDate}</div>
          {book.dateOfTheConclusion && <div className="dateOfTheConclusion"><b>Data em que foi concluído:</b> {book.dateOfTheConclusion}</div>}
          {book.evaluationGrade && <div className="evaluationGrade"><b>Nota de avaliação:</b> {book.evaluationGrade}</div>}
        </div>
      </div>
    );
  }

  renderNoBooks() {
    return (
      <div className="item">
        <div className="content">
          <h5>Não há livros cadastrados com esse status</h5>
        </div>
      </div>
    );
  }

  renderCreate() {
    return (
      <div style={{ textAlign: "right" }}>
        <Link to="/books/new" className="ui button primary">
          Inserir um livro
          </Link>
      </div>
    );
  }

  render() {
    return (
      <div>
        <h1>Livros</h1>

        <h2>Livros que quero ler</h2>
        <div className="ui celled list">{this.renderWantReadBooksList()}</div>

        <h2>Livros que estou lendo</h2>
        <div className="ui celled list">{this.renderReadingBooksList()}</div>

        <h2>Livros já lidos</h2>
        <div className="ui celled list">{this.renderReadedBooksList()}</div>

        {this.renderCreate()}
      </div>
      
    );
  }
}

const mapStateToProps = (state) => {
  return {
    books: Object.values(state.books),
  };
};

export default connect(mapStateToProps, { fetchBooks })(BookList);
