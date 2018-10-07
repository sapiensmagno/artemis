(function() {
    'use strict';

    angular
        .module('artemisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('purchase', {
            parent: 'entity',
            url: '/purchase',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.purchase.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase/purchases.html',
                    controller: 'PurchaseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchase');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('purchase-detail', {
            parent: 'purchase',
            url: '/purchase/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.purchase.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchase/purchase-detail.html',
                    controller: 'PurchaseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchase');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Purchase', function($stateParams, Purchase) {
                    return Purchase.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'purchase',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('purchase-detail.pay', {
            parent: 'purchase-detail',
            url: '/pay/{id}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'Purchase', function($stateParams, $state, $uibModal, Purchase) {
                	return 	Purchase.pay({id : $stateParams.id}).$promise;
                	/*.then($state.go('^')).$promise;*/
                
            }]
        })
        .state('purchase.new', {
            parent: 'purchase',
            url: '/new/{product}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase/purchase-dialog.html',
                    controller: 'PurchaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                        	console.log ('here');
                        	return {
                                quantity: 1,
                                creation: null,
                                status: null,
                                product: {id: $stateParams.product},
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('purchase', null, { reload: 'purchase' });
                }, function() {
                    $state.go('purchase');
                });
            }]
        })
        .state('purchase.edit', {
            parent: 'purchase',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase/purchase-dialog.html',
                    controller: 'PurchaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Purchase', function(Purchase) {
                            return Purchase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase', null, { reload: 'purchase' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchase.delete', {
            parent: 'purchase',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchase/purchase-delete-dialog.html',
                    controller: 'PurchaseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Purchase', function(Purchase) {
                            return Purchase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchase', null, { reload: 'purchase' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
