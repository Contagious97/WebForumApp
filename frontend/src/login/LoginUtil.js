import * as constants from "../Constants/Constants";
import axios from "axios";


const element = document.querySelector('#post-request-error-handling .article-id');
const user = {username: "userName", password: "password"}
export const loginUtil = () => {
    return (dispatch) =>{
        axios.post(constants.LOGIN,user).then(response => dispatch(element.innerHTML = response.data.id))
    }

}