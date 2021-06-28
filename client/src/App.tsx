import React from 'react';
import './styles/app.scss';

import Map from './components/home/Map';
import IntroCard from './components/home/IntroCard';

const App = () => {
  return (
    <div>
      <Map>
        <IntroCard />
      </Map>
    </div>
  );
};

export default App;
