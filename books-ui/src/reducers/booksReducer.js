import _ from "lodash";
import {
  FETCH_BOOK,
  FETCH_BOOKS,
  CREATE_BOOK,
  EDIT_BOOK,
  DELETE_BOOK,
} from "../actions/types";

export default (state = {}, action) => {
  switch (action.type) {
    case FETCH_BOOKS:
      return { ...state, ..._.mapKeys(action.payload, "id") };
    case FETCH_BOOK:
      return { ...state, [action.payload.id]: action.payload };
    case CREATE_BOOK:
      return { ...state, [action.payload.id]: action.payload };
    case EDIT_BOOK:
      return { ...state, [action.payload.id]: action.payload };
    case DELETE_BOOK:
      return _.omit(state, action.payload);
    default:
      return state;
  }
};
