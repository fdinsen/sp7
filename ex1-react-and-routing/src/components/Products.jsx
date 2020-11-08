import React from "react";
import {
    Switch,
    Route,
    Link,
    useParams,
    useRouteMatch
} from "react-router-dom";

export default function Products(props) {
    let { path, url } = useRouteMatch();
    return (
        <>
            <h1>Products</h1>
            <div>
                <ul>
                    {props.bookFacade.getBooks().map(book => {
                        return (<li key={book.id}>
                            {book.title}
                            <Link to={`${url}/${book.id}`}> details</Link>
                        </li>)
                    })}
                </ul>

            </div>
            <Switch>
                <Route exact path={path}>
                    <p>Book details for selected book will go here</p>
                </Route>
                <Route path={`${path}/:bookId`}>
                    <Details bookFacade={props.bookFacade} />
                </Route>
            </Switch>

        </>
    );
}

function Details(props) {
    const { bookId } = useParams();
    const book = props.bookFacade.findBook(bookId);
    return (
        <>
            <div className="details-box">
                <p>Title: {book.title}</p>
                <p>Info: {book.info}</p>
                <p>ID: {book.id}</p>
            </div>
        </>
    );
}