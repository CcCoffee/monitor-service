import React from 'react';
import { Outlet } from 'react-router-dom';
import { Stack } from 'react-bootstrap';
import './Main.css';
import Sidebar from '../components/Sidebar';

function Main() {
  return (
    <Stack className='h-100' direction="horizontal" gap={0}>
      <Sidebar/>
      <div className='h-100 w-100' style={{backgroundColor: '#ebebeb'}}>
        <Outlet/>

      </div>
      
    </Stack>
  );
}

export default Main;