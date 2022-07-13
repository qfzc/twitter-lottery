import React, { useState, useEffect } from 'react';
import {
    Paper,
    Table,
    TableHead,
    TableRow,
    TablePagination,
    Toolbar,
    TableBody,
    TableContainer,
    DialogActions,
    DialogContent,
    Dialog,
    TextField,
    Button,
    Typography,
    DialogTitle,
    TableCell
} from '@mui/material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { get } from 'utils/store';
import api from 'api/api';
import Moment from 'moment';

export default function StickyHeadTable(props) {
    const { columnsData } = props;

    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const [tableData, setTableData] = useState([]);
    const [open, setOpen] = React.useState(false);
    const [url, setUrl] = React.useState('');

    const loadTableData = () => {
        const address = get('fullAddress');
        if (address) {
            api.getWatchUserByAddress(address).then((data) => {
                setTableData(data.data);
            });
        }
    };

    useEffect(() => {
        loadTableData();
    }, []);

    const handleSubmitItem = (event) => {
        setOpen(false);
        const address = get('fullAddress');
        if (address) {
            const data = new FormData();
            data.append('url', url);
            data.append('address', address);
            api.addWatchUser(data).then(() => {
                loadTableData();
            });
        }
    };

    const handleUnbindRel = (row) => {
        console.log('handleUnbindRel');
        const address = get('fullAddress');
        api.unbind(row.id, address);
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
            <TableContainer>
                <Toolbar style={{ width: '100%', display: 'flex', justifyContent: 'space-between' }}>
                    <Typography variant="h3">
                        <strong>监控用户列表</strong>
                    </Typography>
                    <Button
                        type="button"
                        variant="outlined"
                        color="secondary"
                        float="right"
                        startIcon={<AddCircleIcon />}
                        onClick={handleClickOpen}
                    >
                        添加
                    </Button>
                </Toolbar>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {columnsData.map((column) => (
                                <TableCell key={column.accessor} align={column.align} style={{ width: column.minWidth }}>
                                    {column.Header}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody sx={{ maxHeight: '100px' }}>
                        {tableData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(
                            (row) =>
                                row && (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                        {columnsData.map((column) => {
                                            const value = row[column.accessor];
                                            if (column.accessor === 'op') {
                                                return (
                                                    <TableCell
                                                        key={column.accessor}
                                                        align={column.align}
                                                        style={{ width: column.minWidth }}
                                                    >
                                                        <Button variant="outlined" color="secondary" onClick={() => handleUnbindRel(row)}>
                                                            删除
                                                        </Button>
                                                    </TableCell>
                                                );
                                            }
                                            if (column.accessor === 'avatar') {
                                                return (
                                                    <TableCell
                                                        key={column.accessor}
                                                        align={column.align}
                                                        style={{ width: column.minWidth }}
                                                    >
                                                        <img src={value} loading="lazy" alt={value} />
                                                    </TableCell>
                                                );
                                            }
                                            if (column.accessor === 'createdTime') {
                                                return (
                                                    <TableCell
                                                        key={column.accessor}
                                                        align={column.align}
                                                        style={{ width: column.minWidth }}
                                                    >
                                                        {Moment(value).format('YYYY-MM-DD hh:mm')}
                                                    </TableCell>
                                                );
                                            }
                                            return (
                                                <TableCell key={column.accessor} align={column.align} style={{ width: column.minWidth }}>
                                                    {value}
                                                </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                )
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={tableData.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>添加监控用户</DialogTitle>
                <DialogContent>
                    <TextField
                        sx={{
                            width: '300px'
                        }}
                        autoFocus
                        margin="dense"
                        id="urls"
                        label="url"
                        type="url"
                        fullWidth
                        helperText="输入需要个人 Twitter URL"
                        onChange={(event) => setUrl(event.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>取消</Button>
                    <Button onClick={handleSubmitItem}>确认</Button>
                </DialogActions>
            </Dialog>
        </Paper>
    );
}
