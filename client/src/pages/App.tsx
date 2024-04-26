import React from 'react'
import {Route, Routes} from "react-router-dom";
import {Simulation} from "./Simulation.tsx";
import {Home} from "./Home.tsx";
import {Result} from "./Result.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/simulation"} element={<Simulation/>}/>
                <Route path={"/result"} element={<Result/>}/>
            </Routes>
        </>
    )
}
