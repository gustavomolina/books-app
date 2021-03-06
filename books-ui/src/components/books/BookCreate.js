import React from "react";
import { connect } from "react-redux";
import { createBook } from "../../actions";
import BookForm from "./BookForm";

class BookCreate extends React.Component {
  onSubmit = (formValues) => {
    this.props.createBook(formValues);
  };

  render() {
    return (
      <div>
        <h3>Inserir um livro</h3>
        <BookForm onSubmit={this.onSubmit} />
      </div>
    );
  }
}

export default connect(null, { createBook })(BookCreate);
