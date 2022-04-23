import axios from "axios";
import authHeader from "./auth-header";

const API_URL = 'http://localhost:8080/api/'

const getUserBoard = () => {
    return axios.get(API_URL + "dashboard", { headers: authHeader() });
  }

const UserService = {
    getUserBoard,
}

export default UserService