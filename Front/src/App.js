import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";
import Booking from './components/Booking';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import logo from './logo.svg';
import './App.css';

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route path="/" element={<Booking />}>

                    </Route>
                    <Route path="/signin" element={<SignIn />}>

                    </Route>
                    <Route path="/signup" element={<SignUp />}>

                    </Route>
                </Routes>
            </Router>
        </div>
    );
}

export default App;
