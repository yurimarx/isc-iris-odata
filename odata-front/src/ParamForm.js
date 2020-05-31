import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: '25ch',
    },
}));

export default function ParamForm() {

    const classes = useStyles();

    const [param, setParam] = useState({});
    const [load, setLoad] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8080/param/1')
            .then(res => {
                setParam(res.data);
                setLoad(true);
            })
            .catch(err => {
                console.log(err)
                setLoad(true)
            })
    }, []);

    if (load) {
        return (
            <div className={classes.root}>
                <div>
                    <TextField
                        id="host"
                        label="Host for IRIS Instance"
                        placeholder="Host name or IP for IRIS Instance"
                        style={{ margin: 8 }}
                        margin="dense"
                        fullWidth
                        variant="filled"
                        value={param.host}
                        onChange={e => setParam({...param, host: e.target.value})}
                    />
                    <TextField
                        id="port"
                        label="Host Port"
                        placeholder="Host port for IRIS Instance"
                        size={"small"}
                        margin="dense"
                        className={classes.textField}
                        variant="filled"
                        value={param.port}
                        onChange={e => setParam({...param, port: e.target.value})}
                    />
                    <TextField
                        id="namespace"
                        label="Namespace"
                        placeholder="Database repository"
                        size={"medium"}
                        margin="dense"
                        className={classes.textField}
                        variant="filled"
                        value={param.namespace}
                        onChange={e => setParam({...param, namespace: e.target.value})}
                    />
                    <TextField
                        id="schema"
                        label="Schema"
                        placeholder="Database schema"
                        size={"medium"}
                        margin="dense"
                        className={classes.textField}
                        variant="filled"
                        value={param.schema}
                        onChange={e => setParam({...param, schema: e.target.value})}
                    />
                    <TextField
                        id="username"
                        label="Username"
                        placeholder="Database username"
                        size={"medium"}
                        margin="dense"
                        className={classes.textField}
                        variant="filled"
                        value={param.username}
                        onChange={e => setParam({...param, username: e.target.value})}
                    />
                    <TextField
                        id="password"
                        label="Password"
                        placeholder="Database password"
                        size={"medium"}
                        margin="dense"
                        className={classes.textField}
                        variant="filled"
                        value={param.password}
                        onChange={e => setParam({...param, password: e.target.value})}
                    />
                </div>
                <div>
                    <Button style={{ margin: 8 }} onClick={saveParam}
                        variant="contained" color="primary">Submit</Button>
                </div>
            </div>
        );
    } else {
        return (
            <div>
                Loading...
            </div>
        );
    }

    function saveParam() {
        const newParam = {...param, id: 1};
        axios.post('http://localhost:8080/param/', newParam)
            .then(() => {
                alert('Params submit with success.');
            })
            .catch(err => {
                console.log(err)
            })
    }

}
