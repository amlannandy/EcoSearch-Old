import React from 'react';
import './styles/app.scss';

const App = () => {
  return (
    <div>
      <h1>Hello World</h1>
      <button className='btn btn-primary'>Test</button>
      <button className='btn btn-danger btn-block'>Test 1</button>
      <input type='text' className='input-group' />
    </div>
  );
};

export default App;
