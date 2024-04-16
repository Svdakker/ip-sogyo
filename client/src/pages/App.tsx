import React from 'react'
import {Route, Routes} from "react-router-dom";
import {Config} from "./Config.tsx";
import {Result} from "./Result.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <>
            <Routes>
                <Route path={"/"} element={<Config/>}/>
                <Route path={"/result"} element={<Result/>}/>
            </Routes>
        </>
    )
}
