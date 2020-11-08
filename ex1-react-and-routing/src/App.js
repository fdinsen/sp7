import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  NavLink
} from "react-router-dom";
import Home from './components/Home'
import Company from './components/Company'
import Products from './components/Products'
import Header from './components/Header'
import AddBook from './components/AddBook'
import FindBook from './components/FindBook'

function App(props) {
  return (
    <div>
      <Header />
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route path="/products">
          <Products bookFacade={props.bookFacade} />
        </Route>
        <Route path="/company">
          <Company />
        </Route>
        <Route path="/add-book">
          <AddBook bookFacade={props.bookFacade} />
        </Route>
        <Route path="/find-book">
          <FindBook bookFacade={props.bookFacade} />
        </Route>
        <Route>
          <NoMatch />
        </Route>
      </Switch>
    </div>

  );
}

export default App;

function NoMatch() {
  return (
    <h1>Error, page not found</h1>
  )
}