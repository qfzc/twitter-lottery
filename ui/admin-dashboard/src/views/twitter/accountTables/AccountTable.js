import React, { useState, useEffect, useRef } from 'react';
import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    Toolbar,
    Typography,
    Button
} from '@mui/material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import api from 'api/api';
import { get } from 'utils/store';
import ApiInputForm from './ApiInputForm';
import Moment from 'moment';

export default function StickyHeadTable(props) {
    const { columnsData } = props;

    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const [tableData, setTableData] = useState([]);

    const childrenRef = useRef(null);

    const loadTableData = () => {
        const address = get('fullAddress');
        if (address) {
            api.getAccountByAddress(address).then((data) => {
                setTableData(data.data);
            });
        }
    };

    useEffect(() => {
        loadTableData();
    }, []);

    const deleteAccount = (row) => {
        api.delAccount(row.id).then(loadTableData);
    };

    const handleClickOpen = () => {
        childrenRef.current.handleClickOpen();
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
            <TableContainer sx={{ maxHeight: 440 }}>
                <Toolbar style={{ width: '100%', display: 'flex', justifyContent: 'space-between' }}>
                    <Typography variant="h3">
                        <strong>用户列表</strong>
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
                    <TableBody>
                        {tableData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => (
                            <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                {columnsData.map((column) => {
                                    const value = row[column.accessor];
                                    if (column.accessor === 'op') {
                                        return (
                                            <TableCell key={column.accessor} align={column.align} style={{ width: column.minWidth }}>
                                                <Button variant="outlined" color="secondary" onClick={() => deleteAccount(row)}>
                                                    删除
                                                </Button>
                                            </TableCell>
                                        );
                                    }
                                    if (column.accessor === 'avatar') {
                                        return (
                                            <TableCell key={column.accessor} align={column.align} style={{ width: column.minWidth }}>
                                                <img src={value} loading="lazy" alt={value} />
                                            </TableCell>
                                        );
                                    }
                                    if (column.accessor === 'createdTime') {
                                        return (
                                            <TableCell key={column.accessor} align={column.align} style={{ width: column.minWidth }}>
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
                        ))}
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

            <ApiInputForm ref={childrenRef} loadTableData={loadTableData} />
        </Paper>
    );
}
