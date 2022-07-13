import { useState, useRef, useEffect } from 'react';

// material-ui
import { useTheme } from '@mui/material/styles';
import {
    Backdrop,
    Box,
    Chip,
    ClickAwayListener,
    List,
    ListItemButton,
    Paper,
    Popper,
    Stack,
    Typography,
    CircularProgress,
    ListItemIcon,
    ListItemText
} from '@mui/material';
import { ethers } from 'ethers';
import Web3Modal from 'web3modal';
import copy from 'copy-to-clipboard';
import WalletConnectProvider from '@walletconnect/web3-provider';

import { set, get, subscribe } from 'utils/store';
import { IconLogout, IconCopyleft } from '@tabler/icons';
import Transitions from 'ui-component/extended/Transitions';
import MainCard from 'ui-component/cards/MainCard';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import config from 'config';
import ToastService from 'react-material-toast';
import api from 'api/api';

const toast = ToastService.new({
    place: 'topRight',
    duration: 2,
    maxCount: 8
});

const providerOptions = {
    walletconnect: {
        package: WalletConnectProvider,
        options: {
            infuraId: config.INFURA_PROJECT_ID
        }
    }
};

let web3ModelInstance;
if (typeof window !== 'undefined') {
    web3ModelInstance = new Web3Modal({
        network: 'mainnet',
        cacheProvider: true,
        providerOptions
    });
}

let provider;
let signer;
let instance;

async function disconnectWallet() {
    provider = undefined;
    signer = undefined;
    instance = undefined;
    await web3ModelInstance.clearCachedProvider();
}

function formatAddress(address) {
    return `${address.substring(0, 6)}...${address.substring(address.length - 4)}`;
}

function copyAddress() {
    copy(get('fullAddress'));
    toast.success('地址已复制!');
}

function ConnectWallet(props) {
    const theme = useTheme();
    const anchorRef = useRef(null);
    const navigate = useNavigate();

    const [selectedIndex, setSelectedIndex] = useState(-1);
    const [open, setOpen] = useState(false);

    const [address, setAddress] = useState(null);
    const [loading, setLoading] = useState(false);

    async function connectWallet() {
        if (!instance) {
            instance = await web3ModelInstance.connect();
            provider = new ethers.providers.Web3Provider(instance);
            signer = provider.getSigner();
        }
        setLoading(true);

        return { provider, signer, web3Instance: instance };
    }

    const customization = useSelector((state) => state.customization);

    useEffect(() => {
        const addressInStore = get('address') || null;
        if (addressInStore) {
            setAddress(addressInStore);
        }
        subscribe('address', () => {
            const addressInStore = get('address') || null;
            setAddress(addressInStore);
        });
    }, []);

    const handleLogout = async () => {
        console.log('Logout');
    };

    const handleClose = (event) => {
        if (anchorRef.current && anchorRef.current.contains(event.target)) {
            return;
        }
        setOpen(false);
    };

    const handleToggle = () => {
        setOpen((prevOpen) => !prevOpen);
    };

    const handleListItemClick = (event, index, route = '') => {
        setSelectedIndex(index);
        handleClose(event);

        if (route && route !== '') {
            navigate(route);
        }
    };
    if (address && !loading) {
        return (
            <>
                <Chip
                    sx={{
                        height: '52px',
                        width: '150px',
                        alignItems: 'center',
                        borderRadius: '27px',
                        borderColor: theme.palette.primary.light,
                        backgroundColor: theme.palette.primary.light,
                        color: theme.palette.primary.dark
                    }}
                    label={address}
                    variant="outlined"
                    ref={anchorRef}
                    aria-controls={open ? 'menu-list-grow' : undefined}
                    aria-haspopup="true"
                    onClick={handleToggle}
                />
                <Popper
                    placement="bottom-end"
                    open={open}
                    anchorEl={anchorRef.current}
                    role={undefined}
                    transition
                    disablePortal
                    popperOptions={{
                        modifiers: [
                            {
                                name: 'offset',
                                options: {
                                    offset: [0, 14]
                                }
                            }
                        ]
                    }}
                >
                    {({ TransitionProps }) => (
                        <Transitions in={open} {...TransitionProps}>
                            <Paper>
                                <ClickAwayListener onClickAway={handleClose}>
                                    <MainCard border={false} elevation={16} content={false} boxShadow shadow={theme.shadows[16]}>
                                        <PerfectScrollbar
                                            style={{
                                                height: '100%',
                                                maxHeight: 'calc(100vh - 250px)',
                                                overflowX: 'hidden'
                                            }}
                                        >
                                            <Box sx={{ p: 2 }}>
                                                <Stack>
                                                    <Stack direction="row" spacing={0.5} alignItems="center">
                                                        <Typography variant="h4">Wallet address</Typography>
                                                        <Typography component="span" variant="h4" sx={{ fontWeight: 400 }}>
                                                            {address}
                                                        </Typography>
                                                    </Stack>
                                                    <Typography variant="subtitle2">eth</Typography>
                                                </Stack>
                                                <List
                                                    component="nav"
                                                    sx={{
                                                        width: '100%',
                                                        maxWidth: 350,
                                                        minWidth: 300,
                                                        backgroundColor: theme.palette.background.paper,
                                                        borderRadius: '10px',
                                                        [theme.breakpoints.down('md')]: {
                                                            minWidth: '100%'
                                                        },
                                                        '& .MuiListItemButton-root': {
                                                            mt: 0.5
                                                        }
                                                    }}
                                                >
                                                    <ListItemButton
                                                        sx={{ borderRadius: `${customization.borderRadius}px` }}
                                                        selected={selectedIndex === 0}
                                                        onClick={copyAddress}
                                                    >
                                                        <ListItemIcon>
                                                            <IconCopyleft stroke={1.5} size="1.3rem" />
                                                        </ListItemIcon>
                                                        <ListItemText primary={<Typography variant="body2">复制地址</Typography>} />
                                                    </ListItemButton>
                                                    <ListItemButton
                                                        sx={{ borderRadius: `${customization.borderRadius}px` }}
                                                        selected={selectedIndex === 4}
                                                        onClick={handleLogout}
                                                    >
                                                        <ListItemIcon>
                                                            <IconLogout stroke={1.5} size="1.3rem" />
                                                        </ListItemIcon>
                                                        <ListItemText
                                                            primary={<Typography variant="logout">登出</Typography>}
                                                            onClick={async () => {
                                                                await disconnectWallet();
                                                                setAddress(null);
                                                                set('address', '');
                                                                set('fullAddress', '');
                                                            }}
                                                        />
                                                    </ListItemButton>
                                                </List>
                                            </Box>
                                        </PerfectScrollbar>
                                    </MainCard>
                                </ClickAwayListener>
                            </Paper>
                        </Transitions>
                    )}
                </Popper>
            </>
        );
    }
    return (
        <>
            <Chip
                label={loading ? '连接中...' : '连接钱包'}
                variant="outlined"
                ref={anchorRef}
                sx={{
                    height: '52px',
                    width: '150px',
                    alignItems: 'center',
                    borderRadius: '27px',
                    borderColor: theme.palette.primary.light,
                    backgroundColor: theme.palette.primary.light,
                    color: theme.palette.primary.dark
                }}
                onClick={async () => {
                    try {
                        console.log('wallet connect');
                        const { provider, signer, web3Instance } = await connectWallet();
                        const address = await signer.getAddress();
                        const ens = await provider.lookupAddress(address);
                        setAddress(ens || formatAddress(address));
                        set('address', ens || formatAddress(address));
                        set('fullAddress', address);

                        if (address) {
                            await api.registerWallet({ address });
                        }

                        web3Instance.on('accountsChanged', async (accounts) => {
                            if (accounts.length === 0) {
                                await disconnectWallet();
                                set('address', '');
                                set('fullAddress', '');
                                setAddress(null);
                            } else {
                                const address = accounts[0];
                                const ens = await provider.lookupAddress(address);
                                setAddress(ens || formatAddress(address));
                                set('address', ens || formatAddress(address));
                                set('fullAddress', address);
                            }
                        });
                    } catch (err) {
                        toast.error(err.message);
                        await disconnectWallet();
                        set('address', '');
                        set('fullAddress', '');
                        setAddress(null);
                    }
                    setLoading(false);
                }}
            />
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading} onClick={handleClose}>
                <CircularProgress color="inherit" />
            </Backdrop>
        </>
    );
}

export default ConnectWallet;
