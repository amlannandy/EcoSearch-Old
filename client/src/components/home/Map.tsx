import React from 'react';
import GoogleMapReact from 'google-map-react';

interface MapProps {
  children: React.ReactNode;
}

const Map = ({ children }: MapProps) => {
  const defaultProps = {
    center: {
      lat: 10.99835602,
      lng: 77.01502627,
    },
    zoom: 11,
  };

  return (
    <div style={{ height: '100vh', width: '100%' }}>
      <GoogleMapReact
        bootstrapURLKeys={{ key: process.env.REACT_APP_GOOGLE_MAPS_API_KEY! }}
        defaultCenter={defaultProps.center}
        defaultZoom={defaultProps.zoom}></GoogleMapReact>
      <div
        className='container'
        style={{ position: 'absolute', bottom: '20px' }}>
        {children}
      </div>
    </div>
  );
};

export default Map;
