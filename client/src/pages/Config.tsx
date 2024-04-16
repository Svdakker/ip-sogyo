import {useNavigate} from "react-router-dom";

export const Config = () => {
    const navigate = useNavigate()
    return (
        <>
            <div>Welcome?</div>
            <button onClick={() => navigate('/result')}>Run simulation!</button>
        </>
    )
}