import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import styles from './index.module.css';

function SignUp() {
    const [user, setUser] = useState(null);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if(localStorage.getItem('token') && localStorage.getItem('login')) {
            setUser(localStorage.getItem('login'));
            console.log('token detected');
        }
    }, []);

    function handleSignUp() {
        axios.post('http://192.168.192.6:8080/users/signup', {
            login: username,
            password: password
        })
            .then(function (response) {
                if(response.status !== 200) {
                    throw Error;
                }
                console.log(response);
                localStorage.setItem('login', username);
                localStorage.setItem('token', response.data);
                navigate('/');
            })
            .catch(function (error) {
                console.log(error);
                alert('Попробуйте другие логин или пароль!')
            });

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
                        <>user {'  '}<a href={'#'}>Выйти</a></>
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
                    <div className={styles.title}>Регистрация</div>
                    <TextField className={styles.input} onChange={e => setUsername(e.target.value)} label="Username" variant="outlined" />
                    <TextField type={'password'} className={styles.input} onChange={e => setPassword(e.target.value)} label="Password" variant="outlined" />
                    <Button onClick={handleSignUp} className={styles.button} variant="contained">Продолжить</Button>
                </div>
            </div>
        </div>
    );
}

export default SignUp;