// 在index.js中引入axios
import axios from 'axios';
import ToastService from 'react-material-toast';
import { get } from 'utils/store';
import qs from 'qs';

// 保存环境变量
const isPrd = process.env.NODE_ENV === 'production';

// 区分开发环境还是生产环境基础URL
export const baseUrl = 'http://localhost:8080';

// 设置axios基础路径
const instance = axios.create({
    baseURL: baseUrl
});

const toast = ToastService.new({
    place: 'topRight',
    duration: 1,
    maxCount: 8
});

instance.interceptors.request.use(
    (config) => {
        // 登录时就已经把token存到了cookie中
        const address = get('fullAddress');
        if (address) {
            config.headers.authorization = address;
        }
        return config;
    },
    (error) =>
        // 对请求错误做些什么
        Promise.reject(error)
);

instance.interceptors.response.use(
    (response) =>
        // 对响应数据做点什么
        response,
    (error) => {
        // 对响应错误做点什么
        if (error && error.response) {
            switch (error.response.status) {
                case 400:
                    toast.error('错误请求');
                    break;
                case 403:
                    toast.error('拒绝访问');
                    break;
                case 404:
                    toast.error('请求错误，未找到资源');
                    break;
                case 405:
                    toast.error('请求方法未允许');
                    break;
                case 408:
                    toast.error('请求超时');
                    break;
                case 500:
                    toast.error('服务端出错');
                    break;
                case 501:
                    toast.error('网络未实现');
                    break;
                case 502:
                    toast.error('网络错误');
                    break;
                case 503:
                    toast.error('服务不可用');
                    break;
                case 504:
                    toast.error('网络超时');
                    break;
                case 505:
                    toast.error('http版本不支持该请求');
                    break;
                default:
                    toast.error(`连接错误${error.response.status}`);
            }
        }
        return Promise.reject(error);
    }
);

const doPost = (api, data, headers = {}) =>
    new Promise((resolve, reject) => {
        instance
            .post(api, data, { headers })
            .then((res) => {
                resolve(res);
            })
            .catch((error) => {
                reject(error);
            });
    });

const post = (api, data, headers = {}) => {
    headers['Content-Type'] = 'application/x-www-form-urlencoded';
    return doPost(api, qs.stringify(data), headers);
};

const postJson = (api, data, headers = {}) => {
    headers['Content-Type'] = 'application/json;charset=utf-8';
    return doPost(api, JSON.stringify(data), headers);
};
const postFormData = (api, data, headers = {}) => {
    headers['Content-Type'] = 'multipart/form-data';
    return doPost(api, data, headers);
};

const doDelete = (api, data, headers = {}) =>
    new Promise((resolve, reject) => {
        instance
            .delete(api, data, { headers })
            .then((res) => {
                resolve(res);
            })
            .catch((error) => {
                reject(error);
            });
    });

const doGet = (api, params = {}, headers = {}) =>
    new Promise((resolve, reject) => {
        instance
            .get(api, { params, headers })
            .then((res) => {
                resolve(res);
            })
            .catch((error) => {
                reject(error);
            });
    });

export default { doGet, post, postFormData, postJson, doDelete };
