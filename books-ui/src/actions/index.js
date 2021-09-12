import books from "../apis/books";
import history from "../history";
import {
  CREATE_BOOK,
  FETCH_BOOKS,
  FETCH_BOOK,
  DELETE_BOOK,
  EDIT_BOOK,
} from "./types";

export const createBook = (formValues) => async (dispatch) => {
  const response = await books.post("/api/books/", { ...formValues });
  dispatch({ type: CREATE_BOOK, payload: response.data });
  history.push("/");
};

export const fetchBooks = () => async (dispatch) => {
  const response = await books.get("/api/books/");
  dispatch({ type: FETCH_BOOKS, payload: response.data });
};

export const fetchBook = (id) => async (dispatch) => {
  const response = await books.get(`/api/books/${id}/`);
  dispatch({ type: FETCH_BOOK, payload: response.data });
};

export const editBook = (id, formValues) => async (dispatch) => {
  const response = await books.put(`/api/books/${id}/`, formValues);
  dispatch({ type: EDIT_BOOK, payload: response.data });
  history.push("/");
};

export const deleteBook = (id) => async (dispatch) => {
  await books.delete(`/api/books/${id}/`);
  dispatch({ type: DELETE_BOOK, payload: id });
  history.push("/");
};
