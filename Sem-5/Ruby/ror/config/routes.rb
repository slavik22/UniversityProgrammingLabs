Rails.application.routes.draw do
  root 'schools#index'
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"
  resources :schools do
    resources :students
  end
end
