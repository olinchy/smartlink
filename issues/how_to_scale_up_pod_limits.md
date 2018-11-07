0. commands about k8s
    ```
    kubectl get pod -o wide --all-namespaces
    kubectl get node
    kubectl describe node
    ```
0. scale up pods limit from 110 to 200(--max-pods=200)
    ```
    sed "s/--kube-reserved=cpu=500m,memory=500Mi/--kube-reserved=cpu=8,memory=16G/g" -i /etc/kubernetes/kubelet
    sed "s/--minimum-container-ttl-duration=1m/--minimum-container-ttl-duration=1m --max-pods=200/g" -i /etc/kubernetes/kubelet
    systemctl restart kube-apiserver.service
    systemctl restart kube-controller-manager.service
    systemctl restart kube-scheduler.service
    systemctl restart kubelet.service
    systemctl restart kube-proxy
    
    ```
