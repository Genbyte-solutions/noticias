import * as types from "./authTypes";

const initialState = {
  token: null,
  isAuthenticated: false,
  error: null,
};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case types.LOGIN_SUCCESS:
      return {
        ...state,
        token: action.payload,
        isAuthenticated: true,
        error: null,
      };
    case types.LOGIN_FAIL:
      return {
        ...state,
        token: null,
        isAuthenticated: false,
        error: action.error,
      };
    case types.LOGOUT:
      return {
        ...state,
        token: null,
        isAuthenticated: false,
        error: null,
      };
    case types.REGISTER_SUCCESS:
      return {
        ...state,
        token: action.payload,
        isAuthenticated: true,
        error: null,
      };
    case types.REGISTER_FAIL:
      return {
        ...state,
        token: null,
        isAuthenticated: false,
        error: action.error,
      };
    default:
      return state;
  }
};

export default authReducer;
