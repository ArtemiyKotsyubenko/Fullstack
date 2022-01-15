import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import axios from 'axios';
import styles from './index.module.css';

const initialState = [
    {id: 0, user: -1},
    {id: 1, user: -1},
    {id: 2, user: -1},
    {id: 3, user: -1},
    {id: 4, user: -1},
    {id: 5, user: -1},
    {id: 6, user: -1},
    {id: 7, user: -1},
    {id: 8, user: -1},
    {id: 9, user: -1},
    {id: 10, user: -1},
    {id: 11, user: -1}
];

const times = ['9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00']

function Booking() {
    const [timeSlots, setTimeSlots] = useState(initialState);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://192.168.192.6:8080/bookings/getSlots')
            .then(function (response) {
                // handle success
                console.log(response);
                let newTimeSlots = [...timeSlots]
                response.data.map(slot => {
                    newTimeSlots = newTimeSlots.map(ts => ts.id === +slot.time ? {...ts, user: slot.login} : ts)
                });
                setTimeSlots(newTimeSlots);
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            });

        if(localStorage.getItem('token') && localStorage.getItem('login')) {
            setUser(localStorage.getItem('login'));
            console.log('token detected');
        }
    }, []);

    function handleBook(id) {
        if(user === null) {
            navigate('signin');
        }
        if(timeSlots.find(ts => ts.id === id).user === user) {
            setTimeSlots(timeSlots.map(ts => ts.id === id ? {...ts, user: -1} : ts));
            let bodyFormData = new FormData();
            bodyFormData.append('login', user);
            bodyFormData.append('time', id);
            axios({
                method: "post",
                url: "http://192.168.192.6:8080/bookings/unbook",
                data: bodyFormData,
                headers: { "Content-Type": "multipart/form-data" },
            })
                .then(function (response) {
                    if(response.status !== 200) {
                        throw Error;
                    }
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        } else {
            setTimeSlots(timeSlots.map(ts => ts.id === id ? {...ts, user: user} : ts));
            axios.post('http://192.168.192.6:8080/bookings/book', {
                login: user,
                time: id + ''
            })
                .then(function (response) {
                    if(response.status !== 200) {
                        throw Error;
                    }
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }

    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('login');
        setUser(null);
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.menu}>
                <div className={styles.leftmost}>
                    <img className={styles.logo} src={'imgs/logo.png'}></img>
                    <div className={styles.catcafe}>Cat <span className={styles.red}>Cafe</span></div>
                    <div className={styles.menuButton} onClick={() => navigate('/')}>Забронировать котика</div>
                    <div className={styles.menuButton} onClick={() => navigate('cats')}>Наши коты</div>
                </div>
                <div className={styles.rightmost}>
                    {user ? (
                        <>{user} {'  '}<a href={'#'} onClick={logout}>Выйти</a></>
                        ) :
                        <>
                        <a href={'signin'}>Войти</a>
                    {'  /  '}
                        <a href={'signup'}>Регистрация</a>
                        </>
                    }
                </div>
            </div>
            <div className={styles.pagewrapper}>
                <div className={styles.page}>
                    <span className={styles.title}>Забронировать котика</span>
                    <div className={styles.cards}>
                        {timeSlots.map(ts =>
                            <Card className={styles.card} key={ts.id} sx={{minWidth: 275}}>
                                <CardContent>{times[ts.id]}</CardContent>
                                <CardActions>
                                    <Button onClick={() => handleBook(ts.id)} color={ts.user === user ? 'error' : 'primary'} size="small" disabled={ts.user !== user && ts.user !== -1}>{ts.user === -1 ? 'Забронировать' : (ts.user === user ? 'Отменить бронь' : 'Занято')}</Button>
                                </CardActions>
                            </Card>
                        )
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Booking;