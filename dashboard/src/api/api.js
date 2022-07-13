import http from './request';

const getAccountByAddress = (address) => http.doGet(`account/users/${address}`);

const registerWallet = (data) => http.postJson('wallet/users', data);

const delAccount = (address) => http.doDelete(`account/delete/${address}`);

const addAccount = (data) => http.postFormData('account/add', data);

const authUrl = (data) => http.postFormData('account/authUrl', data);

/**
 * 监控用户
 */
const getWatchUserByAddress = (address) => http.doGet(`twitter/users/${address}`);

const addWatchUser = (data) => http.postFormData('twitter/users', data);

const unbind = (tid, address) => http.doDelete(`twitter/unbind/${tid}/${address}`);

export default {
    getAccountByAddress,
    registerWallet,
    addAccount,
    authUrl,
    delAccount,
    unbind,
    getWatchUserByAddress,
    addWatchUser
};
