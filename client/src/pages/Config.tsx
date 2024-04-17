import {useNavigate} from "react-router-dom";
import {UnitOperation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-fermenter.jpg"

export const Config = () => {
    const navigate = useNavigate()
    return (
        <>
            <UnitOperation name={"Batch fermenter"} icon={batch}/>
            <button onClick={() => navigate('/result')}>Go to simulation!</button>
        </>
    )
}