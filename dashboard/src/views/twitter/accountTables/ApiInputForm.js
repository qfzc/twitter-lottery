import React, { useImperativeHandle, forwardRef } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { get } from 'utils/store';

import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import api from 'api/api';

const validationSchema = yup.object({
    apiKey: yup.string('Enter your apiKey').required('apiKey is required'),
    apiSecret: yup
        .string('Enter your apiSecret')
        .min(8, 'Password should be of minimum 8 characters length')
        .required('apiSecret is required')
});

function ApiInputForm(props, ref) {
    const [open, setOpen] = React.useState(false);
    const { loadTableData } = props;

    useImperativeHandle(ref, () => ({
        handleClickOpen: () => {
            setOpen(true);
        }
    }));

    const handleClose = () => {
        setOpen(false);
    };

    const formik = useFormik({
        initialValues: {
            apiKey: '',
            apiSecret: '',
            oauthVerifier: '',
            tag: ''
        },
        validationSchema,
        onSubmit: (values) => {
            setOpen(false);
            const data = new FormData();
            data.append('consumerKey', formik.values.apiKey);
            data.append('oauthVerifier', formik.values.oauthVerifier);
            data.append('address', get('fullAddress'));
            data.append('tag', formik.values.tag);
            api.addAccount(data).then(loadTableData);
        }
    });

    const handleAuthUrl = () => {
        const data = new FormData();
        data.append('consumerKey', formik.values.apiKey);
        data.append('consumerSecret', formik.values.apiSecret);
        api.authUrl(data).then((data) => {
            console.log(data);
            const url = data.data;
            const win = window.open(url, '_blank');
            win.focus();
        });
    };

    return (
        <>
            <Dialog open={open} onClose={handleClose} fullWidth>
                <DialogTitle>添加用户</DialogTitle>
                <DialogContent>
                    <form
                        onSubmit={formik.handleSubmit}
                        style={{
                            display: 'grid',
                            width: '100%',
                            gridRowGap: '10px',
                            padding: '20px'
                        }}
                    >
                        <TextField
                            fullWidth
                            id="apiKey"
                            name="apiKey"
                            label="apiKey"
                            value={formik.values.apiKey}
                            onChange={formik.handleChange}
                            error={formik.touched.apiKey && Boolean(formik.errors.apiKey)}
                            helperText={formik.touched.apiKey && formik.errors.apiKey}
                        />
                        <TextField
                            fullWidth
                            id="apiSecret"
                            name="apiSecret"
                            label="apiSecret"
                            type="password"
                            value={formik.values.apiSecret}
                            onChange={formik.handleChange}
                            error={formik.touched.apiSecret && Boolean(formik.errors.apiSecret)}
                            helperText={formik.touched.apiSecret && formik.errors.apiSecret}
                        />

                        <Button variant="contained" onClick={handleAuthUrl}>
                            点击跳转 Twitter 授权
                        </Button>

                        <TextField
                            fullWidth
                            id="oauthVerifier"
                            name="oauthVerifier"
                            label="从浏览器请求中获取Code（oauthVerifier）"
                            type="text"
                            value={formik.values.oauthVerifier}
                            onChange={formik.handleChange}
                        />

                        <TextField
                            fullWidth
                            id="tag"
                            name="tag"
                            label="@3个好友及其文案"
                            type="text"
                            value={formik.values.tag}
                            onChange={formik.handleChange}
                        />
                        <Button color="primary" variant="contained" fullWidth type="submit">
                            提交
                        </Button>
                    </form>
                </DialogContent>
            </Dialog>
        </>
    );
}

export default forwardRef(ApiInputForm);
