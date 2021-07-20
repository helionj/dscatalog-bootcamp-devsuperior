import axios from 'axios';
import qs from 'qs';

export const BASE_URL = process.env.REACT_APP_BASE_URL ?? 'http://localhost:8080';
const CLIENT_ID = process.env.REACT_APP_CLIENT_ID ?? 'dsCatalog';
const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET ?? 'dsCatalog123';

const basicHeader =  () => 'Basic ' + window.btoa(CLIENT_ID + ':' + CLIENT_SECRET);

type LoginData = {
    username: string,
    password: string
};

type LoginResponse = {
    access_token: string,
    token_type: string,
    expires_in: number,
    scope: string,
    userFirstName: string,
    userId: number
}

export const requestBackendLogin = (loginData : LoginData) => {
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: basicHeader(),

    };

    const data = qs.stringify(
        {
            ...loginData,
            grant_type:'password'
        }
    );

    return axios({method: 'POST', baseURL: BASE_URL, url: '/oauth/token', data, headers});
}

export const saveAuthData = (obj : LoginResponse) => {
    localStorage.setItem('authData', JSON.stringify(obj));
}

export const getAuthData = () => {
    const str = localStorage.getItem('authData') ?? "{}";
    return JSON.parse(str) as LoginResponse;
}