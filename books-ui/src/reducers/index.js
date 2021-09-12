import { combineReducers } from "redux";
import { reducer as formReducer } from "redux-form";
import booksReducer from "./booksReducer";

export default combineReducers({
  form: formReducer,
  books: booksReducer,
});
