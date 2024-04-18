import React from 'react'
import {Route, Routes} from "react-router-dom";
import {Simulation} from "./Simulation.tsx";
import {Home} from "./Home.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/simulation"} element={<Simulation/>}/>
            </Routes>
        </>
    )
}
